package com.kanban.kanban.services;

import com.kanban.kanban.domain.EmployeeDTO;
import com.kanban.kanban.domain.User;
import com.kanban.kanban.exception.ProjectNotFoundException;
import com.kanban.kanban.exception.UserAlreadyExistException;
import com.kanban.kanban.exception.UserNotFoundException;
import com.kanban.kanban.proxy.UserProxy;
import com.kanban.kanban.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    IUserRepository userRepository;

    UserProxy userProxy;

    @Autowired
    public UserService(UserProxy userProxy, IUserRepository iUserRepository) {
        this.userProxy = userProxy;
        this.userRepository = iUserRepository;
    }

    @Override
    public User registerUser(User user) throws UserAlreadyExistException {
        if (userRepository.findById(user.getName()).isEmpty()) {
            EmployeeDTO employeeDTO = new EmployeeDTO(user.getName(), user.getPassword());
            userProxy.addNewUser(employeeDTO);
            return userRepository.insert(user);
        } else {
            throw new UserAlreadyExistException();
        }
    }

    @Override
    public User userDetails(String userName) throws UserNotFoundException {
        if (userRepository.findById(userName).isEmpty()) {
            throw new UserNotFoundException();
        } else {
            return userRepository.findById(userName).get();
        }
    }

    @Override
    public boolean addProjectList(String userName, String projectName) throws UserNotFoundException {
        if (userRepository.findById(userName).isEmpty()) {
            throw new UserNotFoundException();
        } else {
            User user = userRepository.findById(userName).get();
            List<String> list = user.getProjectList();
            list.add(projectName);
            user.setProjectList(list);
            userRepository.save(user);
            return true;
        }
    }

    @Override
    public boolean removeProjectList(String userName, String projectName) throws UserNotFoundException, ProjectNotFoundException {
        if (userRepository.findById(userName).isEmpty()) {
            throw new UserNotFoundException();
        }
        User user_ = userRepository.findById(userName).get();
        List<String> projectList = user_.getProjectList();
        if (!projectList.contains(projectName)) {
            throw new ProjectNotFoundException();
        } else {
            List<String> list = user_.getProjectList();
            list.remove(projectName);
            user_.setProjectList(list);
            userRepository.save(user_);
            return true;
        }
    }

    @Override
    public List<String> getProjectList(String userName) throws UserNotFoundException {
        if (userRepository.findById(userName).isEmpty()) {
            throw new UserNotFoundException();
        } else {
            return userRepository.findById(userName).get().getProjectList();
        }
    }
}