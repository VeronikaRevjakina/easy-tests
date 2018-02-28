package easytests.core.entities;

import easytests.core.models.IssueStandardQuestionTypeOptionModelInterface;
import easytests.core.models.empty.IssueStandardModelEmpty;
import easytests.core.models.empty.QuestionTypeModelEmpty;
import org.junit.Assert;
import org.junit.Test;
import org.meanbean.test.BeanTester;
import org.mockito.Mockito;

/**
 * @author SingularityA
 */
public class IssueStandardQuestionTypeOptionEntityTest {
    @Test
    public void testCommon() throws Exception {
        new BeanTester().testBean(IssueStandardQuestionTypeOptionEntity.class);
    }

    @Test
    public void testMap() throws Exception {
        final Integer id = 3;
        final Integer questionTypeId = 4;
        final Integer minQuestions = 10;
        final Integer maxQuestions = 20;
        final Integer timeLimit = 600;
        final Integer issueStandardId = 12;

        final IssueStandardQuestionTypeOptionModelInterface questionTypeOptionModel
                = Mockito.mock(IssueStandardQuestionTypeOptionModelInterface.class);

        Mockito.when(questionTypeOptionModel.getId()).thenReturn(id);
        Mockito.when(questionTypeOptionModel.getQuestionType()).thenReturn(new QuestionTypeModelEmpty(questionTypeId));
        Mockito.when(questionTypeOptionModel.getMinQuestions()).thenReturn(minQuestions);
        Mockito.when(questionTypeOptionModel.getMaxQuestions()).thenReturn(maxQuestions);
        Mockito.when(questionTypeOptionModel.getTimeLimit()).thenReturn(timeLimit);
        Mockito.when(questionTypeOptionModel.getIssueStandard())
                .thenReturn(new IssueStandardModelEmpty(issueStandardId));

        final IssueStandardQuestionTypeOptionEntity questionTypeOptionEntity
                = new IssueStandardQuestionTypeOptionEntity();
        questionTypeOptionEntity.map(questionTypeOptionModel);

        Assert.assertEquals(id, questionTypeOptionEntity.getId());
        Assert.assertEquals(questionTypeId, questionTypeOptionEntity.getQuestionTypeId());
        Assert.assertEquals(minQuestions, questionTypeOptionEntity.getMinQuestions());
        Assert.assertEquals(maxQuestions, questionTypeOptionEntity.getMaxQuestions());
        Assert.assertEquals(timeLimit, questionTypeOptionEntity.getTimeLimit());
        Assert.assertEquals(issueStandardId, questionTypeOptionEntity.getIssueStandardId());
    }
}
