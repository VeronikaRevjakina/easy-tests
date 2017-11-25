package easytests.core.services;

import easytests.core.entities.UserEntity;
import easytests.core.mappers.UsersMapper;
import easytests.core.models.UserModelInterface;
import easytests.core.options.UsersOptionsInterface;
import easytests.core.services.exceptions.DeleteUnidentifiedModelException;
import easytests.support.UsersSupport;
import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.*;
import static org.mockito.BDDMockito.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.test.context.junit4.*;


/**
 * @author malinink
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersServiceTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @MockBean
    private UsersMapper usersMapper;

    @Autowired
    private UsersService usersService;

    private UsersSupport usersSupport = new UsersSupport();

    private List<UserEntity> getUsersFixturesEntities() {
        final List<UserEntity> usersEntities = new ArrayList<>(2);
        usersEntities.add(this.usersSupport.getEntityFixtureMock(0));
        usersEntities.add(this.usersSupport.getEntityFixtureMock(1));
        return usersEntities;
    }

    private List<UserModelInterface> getUsersFixturesModels() {
        final List<UserModelInterface> usersModels = new ArrayList<>(2);
        usersModels.add(this.usersSupport.getModelFixtureMock(0));
        usersModels.add(this.usersSupport.getModelFixtureMock(1));
        return usersModels;
    }

    private void assertEquals(List<UserModelInterface> first, List<UserModelInterface> second) {
        Assert.assertEquals(first.size(), second.size());
        Integer i = 0;
        for (UserModelInterface userModel: first) {
            this.usersSupport.assertEquals(userModel, second.get(i));
            i++;
        }
    }

    @Test
    public void testFindAllPresentList() throws Exception {
        final List<UserEntity> usersEntities = this.getUsersFixturesEntities();
        when(this.usersMapper.findAll()).thenReturn(usersEntities);

        final List<UserModelInterface> usersModels = this.usersService.findAll();

        this.assertEquals(this.getUsersFixturesModels(), usersModels);
    }

    @Test
    public void testFindAllAbsentList() throws Exception {
        when(this.usersMapper.findAll()).thenReturn(new ArrayList<>(0));

        final List<UserModelInterface> usersModels = this.usersService.findAll();

        Assert.assertEquals(0, usersModels.size());
    }

    @Test
    public void testFindAllWithOptions() throws Exception {
        final ArgumentCaptor<List> listCaptor = ArgumentCaptor.forClass(List.class);
        final List<UserModelInterface> usersModels = this.getUsersFixturesModels();
        final List<UserEntity> usersEntities = this.getUsersFixturesEntities();
        final UsersOptionsInterface usersOptions = mock(UsersOptionsInterface.class);
        when(this.usersMapper.findAll()).thenReturn(usersEntities);
        when(usersOptions.withRelations(listCaptor.capture())).thenReturn(usersModels);

        final List<UserModelInterface> usersFoundedModels = this.usersService.findAll(usersOptions);

        this.assertEquals(usersModels, listCaptor.getValue());
        Assert.assertSame(usersModels, usersFoundedModels);
        verify(this.usersMapper, times(1)).findAll();
        verifyNoMoreInteractions(this.usersMapper);
    }

    @Test
    public void testFindPresentModel() throws Exception {
        final Integer presentId = 1;
        final UserEntity userEntity = this.usersSupport.getEntityFixtureMock(0);
        when(this.usersMapper.find(presentId)).thenReturn(userEntity);

        final UserModelInterface userFoundedModel = this.usersService.find(presentId);

        this.usersSupport.assertEquals(this.usersSupport.getModelFixtureMock(0), userFoundedModel);
    }

    @Test
    public void testFindAbsentModel() throws Exception {
        final Integer absentId = 12;
        when(this.usersMapper.find(absentId)).thenReturn(null);

        final UserModelInterface userFoundedModel = this.usersService.find(absentId);

        Assert.assertNull(userFoundedModel);
    }

    @Test
    public void testFindWithOptions() throws Exception {
        final ArgumentCaptor<UserModelInterface> userModelCaptor = ArgumentCaptor.forClass(UserModelInterface.class);
        final UserModelInterface userModel = this.usersSupport.getModelFixtureMock(0);
        final UserEntity userEntity = this.usersSupport.getEntityFixtureMock(0);
        final UsersOptionsInterface usersOptions = mock(UsersOptionsInterface.class);
        when(this.usersMapper.find(userModel.getId())).thenReturn(userEntity);
        when(usersOptions.withRelations(userModelCaptor.capture())).thenReturn(userModel);

        final UserModelInterface userFoundedModel = this.usersService.find(userModel.getId(), usersOptions);

        this.usersSupport.assertEquals(userModel, userModelCaptor.getValue());
        Assert.assertSame(userModel, userFoundedModel);
        verify(this.usersMapper, times(1)).find(userModel.getId());
        verifyNoMoreInteractions(this.usersMapper);
    }


    @Test
    public void testFindByEmailPresentModel() throws Exception {
        final UserEntity userEntity = this.usersSupport.getEntityFixtureMock(0);
        when(this.usersMapper.findByEmail(userEntity.getEmail())).thenReturn(userEntity);

        final UserModelInterface userFoundedModel = this.usersService.findByEmail(userEntity.getEmail());

        this.usersSupport.assertEquals(this.usersSupport.getModelFixtureMock(0), userFoundedModel);
    }

    @Test
    public void testFindByEmailAbsentModel() throws Exception {
        final String absentEmail = "absent.email@gmail.com";
        when(this.usersMapper.findByEmail(absentEmail)).thenReturn(null);

        final UserModelInterface userFoundedModel = this.usersService.findByEmail(absentEmail);

        Assert.assertNull(userFoundedModel);
    }

    @Test
    public void testFindByEmailWithOptions() throws Exception {
        final ArgumentCaptor<UserModelInterface> userModelCaptor = ArgumentCaptor.forClass(UserModelInterface.class);
        final UserModelInterface userModel = this.usersSupport.getModelFixtureMock(0);
        final UserEntity userEntity = this.usersSupport.getEntityFixtureMock(0);
        final UsersOptionsInterface usersOptions = Mockito.mock(UsersOptionsInterface.class);
        when(this.usersMapper.findByEmail(userEntity.getEmail())).thenReturn(userEntity);
        when(usersOptions.withRelations(userModelCaptor.capture())).thenReturn(userModel);

        final UserModelInterface userFoundedModel = this.usersService.findByEmail(userEntity.getEmail(), usersOptions);

        this.usersSupport.assertEquals(userModel, userModelCaptor.getValue());
        Assert.assertSame(userModel, userFoundedModel);
        verify(this.usersMapper, times(1)).findByEmail(userEntity.getEmail());
        verifyNoMoreInteractions(this.usersMapper);
    }

    @Test
    public void testSaveCreatesEntity() throws Exception {
        final ArgumentCaptor<UserEntity> userEntityCaptor = ArgumentCaptor.forClass(UserEntity.class);

        this.usersService.save(this.usersSupport.getModelAdditionalMock(0));

        verify(this.usersMapper, times(1)).insert(userEntityCaptor.capture());
        this.usersSupport.assertEquals(this.usersSupport.getEntityAdditionalMock(0), userEntityCaptor.getValue());
    }

    @Test
    public void testSaveUpdateEntityIdOnCreation() throws Exception {
        final UserModelInterface userAdditionalModel = this.usersSupport.getModelAdditionalMock(0);
        doAnswer(invocation -> {
            final UserEntity userEntity = (UserEntity) invocation.getArguments()[0];
            userEntity.setId(5);
            return null;
        }).when(this.usersMapper).insert(Mockito.any(UserEntity.class));

        this.usersService.save(userAdditionalModel);

        verify(userAdditionalModel, times(1)).setId(5);
    }

    @Test
    public void testSaveUpdatesEntity() throws Exception {
        final ArgumentCaptor<UserEntity> userEntityCaptor = ArgumentCaptor.forClass(UserEntity.class);
        this.usersService.save(this.usersSupport.getModelFixtureMock(0));

        verify(this.usersMapper, times(1)).update(userEntityCaptor.capture());
        this.usersSupport.assertEquals(this.usersSupport.getEntityFixtureMock(0), userEntityCaptor.getValue());
    }

    @Test
    public void testSaveWithOptions() throws Exception {
        final UserModelInterface userModel = this.usersSupport.getModelFixtureMock(0);
        final UsersOptionsInterface usersOptions = Mockito.mock(UsersOptionsInterface.class);

        this.usersService.save(userModel, usersOptions);

        verify(usersOptions, times(1)).saveWithRelations(userModel);
        verifyNoMoreInteractions(this.usersMapper);
    }

    @Test
    public void testDeleteIdentifiedModel() throws Exception {
        final ArgumentCaptor<UserEntity> userEntityCaptor = ArgumentCaptor.forClass(UserEntity.class);
        this.usersService.delete(this.usersSupport.getModelFixtureMock(0));

        verify(this.usersMapper, times(1)).delete(userEntityCaptor.capture());
        this.usersSupport.assertEquals(this.usersSupport.getEntityFixtureMock(0), userEntityCaptor.getValue());
    }

    @Test
    public void testDeleteUnidentifiedModelTrowsException() throws Exception {
        final UserModelInterface userModel = this.usersSupport.getModelAdditionalMock(0);

        exception.expect(DeleteUnidentifiedModelException.class);
        this.usersService.delete(userModel);
    }

    @Test
    public void testDeleteWithOptions() throws Exception {
        final UserModelInterface userModel = this.usersSupport.getModelFixtureMock(0);
        final UsersOptionsInterface usersOptions = Mockito.mock(UsersOptionsInterface.class);

        this.usersService.delete(userModel, usersOptions);

        verify(usersOptions, times(1)).deleteWithRelations(userModel);
        verifyNoMoreInteractions(this.usersMapper);
    }

}
