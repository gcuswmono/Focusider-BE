package mono.focusider.domain.quiz.service;

import lombok.RequiredArgsConstructor;
import mono.focusider.domain.member.domain.Member;
import mono.focusider.domain.member.helper.MemberHelper;
import mono.focusider.domain.quiz.domain.*;
import mono.focusider.domain.quiz.dto.info.QuizInfo;
import mono.focusider.domain.quiz.dto.info.QuizSetInfo;
import mono.focusider.domain.quiz.dto.req.QuizCheckReReqDto;
import mono.focusider.domain.quiz.dto.req.QuizCheckReqDto;
import mono.focusider.domain.quiz.dto.res.QuizCheckResDto;
import mono.focusider.domain.quiz.dto.res.QuizGetResDto;
import mono.focusider.domain.quiz.dto.res.QuizWrongResDto;
import mono.focusider.domain.quiz.helper.*;
import mono.focusider.domain.quiz.mapper.ChoiceMapper;
import mono.focusider.domain.quiz.mapper.QuizAttemptMapper;
import mono.focusider.domain.quiz.type.QuizStatusType;
import mono.focusider.global.aspect.member.MemberInfoParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final QuizAttemptMapper quizAttemptMapper;

    public QuizGetResDto findQuizByLevel(MemberInfoParam memberInfoParam) {
        return quizHelper.findQuizInfoByLevelAndMemberId(memberInfoParam.memberLevel(), memberInfoParam.memberId());
    }

    @Transactional
    public QuizCheckResDto checkAndSaveQuiz(QuizCheckReqDto quizCheckReqDto, MemberInfoParam memberInfoParam) {
        Member member = memberHelper.findMemberByIdOrThrow(memberInfoParam.memberId());
        Quiz quiz = quizHelper.findAllDataByQuizId(quizCheckReqDto.quizId(), quizCheckReqDto.choiceId());
        List<Choice> choices = quiz.getChoices();
        Choice correctChoice = findCorrectChoice(choices);
        Choice userChoice = findUserChoice(choices, quizCheckReqDto.choiceId());
        QuizStatusType quizStatusType = checkCorrect(userChoice.getContent(), correctChoice.getContent());
        String commentaryContent = correctChoice.getQuiz().getCommentary().getContent();
        quizAttemptHelper.createAndSaveQuizAttempt(quiz, member, quizStatusType, quizCheckReqDto.time());
        return choiceMapper.toQuizCheckResDto(correctChoice.getContent(), userChoice.getContent(), commentaryContent);
    }

    @Transactional
    public QuizCheckResDto checkAndUpdateQuiz(QuizCheckReReqDto quizCheckReReqDto) {
        QuizAttempt quizAttempt = quizAttemptHelper
                .findQuizAttemptAndQuizById(quizCheckReReqDto.quizAttemptId(), quizCheckReReqDto.choiceId());
        List<Choice> choices = quizAttempt.getQuiz().getChoices();
        Choice correctChoice = findCorrectChoice(choices);
        Choice userChoice = findUserChoice(choices, quizCheckReReqDto.choiceId());
        String commentaryContent = quizAttempt.getQuiz().getCommentary().getContent();
        QuizStatusType quizStatusType = checkCorrect(userChoice.getContent(), correctChoice.getContent());
        quizAttempt.updateQuizStatusAndTime(quizStatusType, quizCheckReReqDto.time());
        return choiceMapper.toQuizCheckResDto(correctChoice.getContent(), userChoice.getContent(), commentaryContent);
    }

    public QuizWrongResDto findWrongQuizList(MemberInfoParam memberInfoParam, Pageable pageable) {
        Page<QuizInfo> wrongQuizInfo = quizAttemptHelper.findWrongQuizInfo(memberInfoParam.memberId(), pageable);
        return quizAttemptMapper.toQuizWrongResDto(wrongQuizInfo);
    }

    public QuizGetResDto findQuizById(Long quizId) {
        return quizHelper.findQuizInfoById(quizId);
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

    private QuizStatusType checkCorrect(String userChoiceContent, String answerChoiceContent) {
        return userChoiceContent.equals(answerChoiceContent) ? QuizStatusType.QUIZ_CORRECT : QuizStatusType.QUIZ_INCORRECT;
    }

    private Choice findCorrectChoice(List<Choice> choices) {
        return choices.stream()
                .filter(Choice::getIsAnswer)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Correct choice not found"));
    }

    private Choice findUserChoice(List<Choice> choices, Long choiceId) {
        return choices.stream()
                .filter(choice -> choice.getId().equals(choiceId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("User's choice not found"));
    }
}
