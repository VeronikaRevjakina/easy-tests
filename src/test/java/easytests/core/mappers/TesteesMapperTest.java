package easytests.core.mappers;

import easytests.core.entities.TesteeEntity;
import easytests.support.TesteesSupport;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author janchk
 */
public class TesteesMapperTest extends AbstractMapperTest {

    protected TesteesSupport testeesSupport = new TesteesSupport();

    @Autowired
    private TesteesMapper testeesMapper;

    @Test
    // 20min
    public void testFindAll() throws Exception {
        final List<TesteeEntity> testeesEntities = this.testeesMapper.findAll();
        Assert.assertEquals(3, testeesEntities.size());

        Integer index = 0;
        for (TesteeEntity testeeEntity: testeesEntities){
            final TesteeEntity testeeFixtureEntity = this.testeesSupport.getTesteeFixtureMock(index);
            this.testeesSupport.assertEquals(testeeEntity, testeeFixtureEntity);
            index++;
        }
    }

    @Test
    public void testFind() throws Exception {
        final TesteeEntity testee = this.testeesMapper.find(1);
        final TesteeEntity testeeFixtureEntity = this.testeesSupport.getTesteeFixtureMock(0);

        this.testeesSupport.assertEquals(testee, testeeFixtureEntity);
    }

    @Test
    //20min
    // Возможно надо реализовать в support классе метод поиска по id Quiz'a, чтобы логику соблюсти.
    public void testFindByQuizId() throws Exception {
        final TesteeEntity testee = this.testeesMapper.findByQuizId(3);
        final TesteeEntity testeeFixtureEntity = this.testeesSupport.getTesteeFixtureMock(2);

        this.testeesSupport.assertEquals(testeeFixtureEntity, testee);
    }

    @Test
    //1hr
    public void testInsert() throws Exception{
        final ArgumentCaptor<Integer> id = ArgumentCaptor.forClass(Integer.class);
        final TesteeEntity testeeAdditionalEntity = this.testeesSupport.getTesteeAdditionalMock(0);
        this.testeesMapper.insert(testeeAdditionalEntity);

        verify(testeeAdditionalEntity, times(1)).setId(id.capture());
        Assert.assertNotNull(id.getValue());

        final TesteeEntity testeeInsertedEntity = this.testeesMapper.find(id.getValue());

        Assert.assertNotNull(testeeInsertedEntity);
        this.testeesSupport.assertEqualsWithoutId(testeeAdditionalEntity, testeeInsertedEntity);

    }

    @Test
    // 1hr
    public void testUpdate() throws Exception{
        final TesteeEntity testeeAdditionalEntity = this.testeesSupport.getTesteeAdditionalMock(1);
        final Integer id = testeeAdditionalEntity.getId();
        final TesteeEntity testeeEntity = this.testeesMapper.find(id);

        Assert.assertNotNull(testeeEntity);
        this.testeesSupport.assertNotEqualsWithoutId(testeeAdditionalEntity, testeeEntity);

        this.testeesMapper.update(testeeAdditionalEntity);
        final TesteeEntity updatedTesteeEntity = this.testeesMapper.find(id);

        this.testeesSupport.assertEquals(testeeAdditionalEntity, updatedTesteeEntity);
    }

    @Test
    public void testDelete() throws Exception{
        TesteeEntity testeeEntity= this.testeesMapper.find(1);
        Assert.assertNotNull(testeeEntity);

        this.testeesMapper.delete(testeeEntity);
        testeeEntity = this.testeesMapper.find(1);
        Assert.assertNull(testeeEntity);
    }

}
