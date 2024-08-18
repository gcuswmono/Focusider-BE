package mono.focusider.domain.quiz.service;

import lombok.RequiredArgsConstructor;
import mono.focusider.domain.member.domain.Member;
import mono.focusider.domain.member.helper.MemberHelper;
import mono.focusider.domain.quiz.domain.Choice;
import mono.focusider.domain.quiz.domain.Commentary;
import mono.focusider.domain.quiz.domain.Keyword;
import mono.focusider.domain.quiz.domain.Quiz;
import mono.focusider.domain.quiz.dto.info.QuizSetInfo;
import mono.focusider.domain.quiz.dto.req.QuizCheckReqDto;
import mono.focusider.domain.quiz.dto.res.QuizCheckResDto;
import mono.focusider.domain.quiz.dto.res.QuizGetResDto;
import mono.focusider.domain.quiz.helper.*;
import mono.focusider.domain.quiz.mapper.ChoiceMapper;
import mono.focusider.domain.quiz.type.QuizStatusType;
import mono.focusider.global.aspect.member.MemberInfoParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuizService {
    private final CommentaryHelper commentaryHelper;
    private final MemberHelper memberHelper;
    private final QuizHelper quizHelper;
    private final QuizKeywordHelper quizKeywordHelper;
    private final KeywordHelper keywordHelper;
    private final ChoiceHelper choiceHelper;
    private final ChoiceMapper choiceMapper;
    private final QuizAttemptHelper quizAttemptHelper;

    public QuizGetResDto findQuizByLevel(MemberInfoParam memberInfoParam) {
        return quizHelper.findQuizInfoByLevelAndMemberId(memberInfoParam.memberLevel(), memberInfoParam.memberId());
    }

    public QuizCheckResDto checkQuiz(QuizCheckReqDto quizCheckReqDto, MemberInfoParam memberInfoParam) {
        Member member = memberHelper.findMemberByIdOrThrow(memberInfoParam.memberId());
        QuizStatusType quizStatusType = QuizStatusType.QUIZ_INCORRECT;
        List<Choice> choices = choiceHelper.getChoices(quizCheckReqDto.quizId(), quizCheckReqDto.choiceId());
        Choice correctChoice = findCorrectChoice(choices);
        Choice userChoice = findUserChoice(choices, quizCheckReqDto);
        Quiz quiz = correctChoice.getQuiz();

        String userContent = userChoice.getContent();
        String correctContent = correctChoice.getContent();
        String commentaryContent = correctChoice.getQuiz().getCommentary().getContent();

        if (userChoice.equals(correctChoice)) {
            quizStatusType = QuizStatusType.QUIZ_CORRECT;
        }
        quizAttemptHelper.createAndSaveQuizAttempt(quiz, member, quizStatusType);
        return choiceMapper.toQuizCheckResDto(correctContent, userContent, commentaryContent);
    }

    @Transactional
    public void createAndSaveQuiz(QuizSetInfo quizSetInfo) {
        Commentary commentary = commentaryHelper.createAndSaveCommentary(quizSetInfo.commentaryInfo());
        Quiz quiz = quizHelper.createAndSaveQuiz(quizSetInfo.title(), quizSetInfo.content(), quizSetInfo.level(), commentary);
        List<Keyword> keywords = keywordHelper.findByKeywordType(quizSetInfo.keywordTypes());
        keywords.forEach(keyword -> {
            quizKeywordHelper.createAndSaveQuizKeyword(quiz, keyword);
        });
        quizSetInfo.choiceSetInfos().forEach(choiceInfo -> {
            choiceHelper.createAndSaveChoice(quiz, choiceInfo);
        });
    }

    private Choice findCorrectChoice(List<Choice> choices) {
        return choices.stream()
                .filter(Choice::getIsAnswer)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Correct choice not found"));
    }

    private Choice findUserChoice(List<Choice> choices, QuizCheckReqDto quizCheckReqDto) {
        return choices.stream()
                .filter(choice -> choice.getId().equals(quizCheckReqDto.choiceId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("User's choice not found"));
    }
}
