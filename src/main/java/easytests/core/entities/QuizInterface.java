package easytests.core.entities;

import java.util.List;

/**
 * @author malinink
 * @deprecated because of models
 */

@Deprecated
public interface QuizInterface extends EntityInterface {

    QuizInterface setId(Integer id);

    List<PointInterface> getPoints();

    QuizInterface setPoints(List<PointInterface> points);

    Integer getIssueId();

    QuizInterface setIssueId(Integer issueId);

    String getInviteCode();

    QuizInterface setInviteCode(String inviteCode);

}
