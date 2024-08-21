package mono.focusider;

import mono.focusider.domain.category.domain.Category;
import mono.focusider.domain.category.repository.category.CategoryRepository;
import mono.focusider.domain.category.type.CategoryType;
import mono.focusider.domain.quiz.domain.*;
import mono.focusider.domain.quiz.repository.choice.ChoiceRepository;
import mono.focusider.domain.quiz.repository.commentary.CommentaryRepository;
import mono.focusider.domain.quiz.repository.keyword.KeywordRepository;
import mono.focusider.domain.quiz.repository.quiz.QuizKeywordRepository;
import mono.focusider.domain.quiz.repository.quiz.QuizRepository;
import mono.focusider.domain.quiz.type.KeywordType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@ActiveProfiles("dev")
public class InitDb {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    ChoiceRepository choiceRepository;

    @Autowired
    QuizKeywordRepository quizKeywordRepository;

    @Autowired
    KeywordRepository keywordRepository;

    @Autowired
    CommentaryRepository commentaryRepository;


    @Test
    public void saveQuiz() {

    }


    private void inputQuiz1() {
        Keyword voca = keywordRepository.findById(1L).orElseThrow();
        Keyword prov = keywordRepository.findById(1L).orElseThrow();
        Keyword four = keywordRepository.findById(1L).orElseThrow();
        Keyword words = keywordRepository.findById(1L).orElseThrow();
        Keyword gram = keywordRepository.findById(1L).orElseThrow();

        //해설 아이디, 해설 내용, /퀴즈 아이디, 퀴즈 타이틀, 퀴즈 컨텐츠, 레벨, /퀴즈 키워드 아이디, 키워드 여러개, /선택지 아이디, 컨텐츠, 정답 여부
        Commentary commentary = makeCommentary(1L,"\"그 학생은 참 이지적이야.\"라는 말을 들었을 때, 가장 적절한 반응은 3번 \"여러모로 똑똑한 학생인가 보네.\"입니다.\n" +
                "왜냐하면 \"이지적\"이라는 말은 \"지적으로 아주 뛰어나다\"는 뜻이에요. \n" +
                "그래서 이 말을 듣고 \"여러모로 똑똑한 학생인가 보네.\"라고 말하는 게 가장 좋은 반응이 될 거예요.\n" +
                "다른 반응들은 적절하지 않아요. \n" +
                "1번은 그 학생을 나무라는 것 같고, \n" +
                "2번은 그 학생이 자기만 안다고 생각하는 것 같은 말이며, \n" +
                "4번은 그 학생이 무언가 잘못했다고 생각하는 것 같은 말이에요.\n" +
                "그래서 \"여러모로 똑똑한 학생인가 보네.\"라고 말하는 게 가장 적절한 반응이 될 거예요.");

        Quiz quiz = makeQuiz(1L, "다음 말을 들은 상대의 반응으로 적절한 것은?", "\"그 학생은 참 이지적이야.\"", 1, commentary);

        makeQuizKeyword(1L, quiz, voca);
        makeQuizKeyword(2L, quiz, words);

        List<Choice> choices = Arrays.asList(
                new Choice(1L, quiz, "너는 사람을 얕잡아 보는 경향이 있어.", false),
                new Choice(2L, quiz, "그 학생은 자기만 아는가 보구나?", false),
                new Choice(3L, quiz, "여러모로 똑똑한 학생인가 보네.", true),
                new Choice(4L, quiz, "그 학생이 너한테 뭐 잘못했니?", false)
        );

        choiceRepository.saveAll(choices);
    }

    private Commentary makeCommentary(Long id, String content) {
        Commentary commentary = new Commentary(id, content);
        return commentaryRepository.save(commentary);
    }

    private Quiz makeQuiz(Long id, String title, String content, Integer level, Commentary commentary) {
        Quiz quiz = new Quiz(id, title, content, level, commentary, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        return quizRepository.save(quiz);
    }

    private void makeQuizKeyword(Long id, Quiz quiz, Keyword keyword) {
        QuizKeyword quizKeyword = new QuizKeyword(id, quiz, keyword);
        quizKeywordRepository.save(quizKeyword);
    }

    @Test
    public void inputKeyword() {
        List<Keyword> keywords = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            keywords.add(makeKeyword(i + 1));
        }
        keywordRepository.saveAll(keywords);
    }

    @Test
    public void inputCategory() {
        List<Category> categories = new ArrayList<>();
        for(int i = 0; i < 6; i++) {
            categories.add(makeCategory((long)(i + 1)));
        }
        categoryRepository.saveAll(categories);
    }

    private Category makeCategory(long id) {
        CategoryType categoryType = null;
        switch ((int) id) {
            case 1:
                categoryType = CategoryType.ART;
                break;
            case 2:
                categoryType = CategoryType.SCIENCE;
                break;
            case 3:
                categoryType = CategoryType.SOCIETY;
                break;
            case 4:
                categoryType = CategoryType.TECHNOLOGY;
                break;
            case 5:
                categoryType = CategoryType.HUMANITIES;
                break;
            case 6:
                categoryType = CategoryType.AMALGAMATION;
        }
        return new Category(id, categoryType);
    }

    private Keyword makeKeyword(long id) {
        KeywordType keywordType = null;
        switch ((int) id) {
            case 1:
                keywordType = KeywordType.VOCABULARY;
                break;
            case 2:
                keywordType = KeywordType.PROVERB;
                break;
            case 3:
                keywordType = KeywordType.FOUR_IDIOMS;
                break;
            case 4:
                keywordType = KeywordType.WORDS_AND_EXPRESSIONS;
                break;
            case 5:
                keywordType = KeywordType.GRAMMAR;
                break;
        }
        return new Keyword(id,keywordType);
    }
}
