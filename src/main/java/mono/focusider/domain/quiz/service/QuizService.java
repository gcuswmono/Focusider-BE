package mono.focusider.domain.quiz.service;

import lombok.RequiredArgsConstructor;
import mono.focusider.domain.quiz.domain.Commentary;
import mono.focusider.domain.quiz.domain.Keyword;
import mono.focusider.domain.quiz.domain.Quiz;
import mono.focusider.domain.quiz.dto.info.QuizSetInfo;
import mono.focusider.domain.quiz.dto.res.QuizInfo;
import mono.focusider.domain.quiz.helper.*;
import mono.focusider.domain.quiz.mapper.ChoiceMapper;
import mono.focusider.domain.quiz.mapper.QuizMapper;
import mono.focusider.global.aspect.member.MemberInfoParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuizService {
    private final CommentaryHelper commentaryHelper;
    private final QuizHelper quizHelper;
    private final QuizKeywordHelper quizKeywordHelper;
    private final KeywordHelper keywordHelper;
    private final ChoiceHelper choiceHelper;
    private final ChoiceMapper choiceMapper;
    private final QuizMapper quizMapper;

    public QuizInfo findQuizByLevel(MemberInfoParam memberInfoParam) {
        return quizHelper.findQuizInfoByLevelAndMemberId(memberInfoParam.memberLevel(), memberInfoParam.memberId());
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
}
