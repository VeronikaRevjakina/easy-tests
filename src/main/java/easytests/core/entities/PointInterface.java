package easytests.core.entities;

import java.util.List;

/**
 * @author malinink
 * @deprecated cause of models
 */
@Deprecated
public interface PointInterface extends EntityInterface {
    PointInterface setId(Integer id);

    String getText();

    PointInterface setText(String text);

    String getType();

    PointInterface setType(String type);

    Integer getQuizId();

    PointInterface setQuizId(Integer quizId);

    List<SolutionInterface> getSolutions();

    PointInterface setSolutions(List<SolutionInterface> solutions);
}
