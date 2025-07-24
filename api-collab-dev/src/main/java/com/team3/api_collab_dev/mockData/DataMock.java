package com.team3.api_collab_dev.mockData;

import com.team3.api_collab_dev.entity.Comment;
import com.team3.api_collab_dev.entity.Project;
import com.team3.api_collab_dev.entity.Task;
import com.team3.api_collab_dev.entity.User;
import com.team3.api_collab_dev.enumType.Domain;
import com.team3.api_collab_dev.enumType.Level;
import com.team3.api_collab_dev.enumType.Status;
import com.team3.api_collab_dev.enumType.ValidationType;

import java.util.List;

public class DataMock {

    public String projectInstanceData() {

        // get project by his id

        User author = new User();
        author.setId(1L); // ou récupéré depuis la DB
        author.setPseudo("authorUser");
        author.setEmail("author@example.com");
        // ... autres champs si besoin

        User manager = new User();
        manager.setId(2L);
        manager.setPseudo("managerUser");
        manager.setEmail("manager@example.com");
        // ... autres champs si besoin

        // Créer quelques commentaires
        Comment comment1 = new Comment();
        comment1.setAuthor(author);
        comment1.setProject(null); // on l'associera après
        comment1.setContent("Très bon projet !");

        Comment comment2 = new Comment();
        comment2.setAuthor(manager);
        comment2.setProject(null); // on l'associera après
        comment2.setContent("Je valide les specs.");

        List<Comment> commentList = List.of(comment1, comment2);

        // Créer quelques tâches
        Task task1 = new Task();
        task1.setTaskName("Créer l'API");
        task1.setCoins(20);
        task1.setStatus(Status.DONE);
        task1.setIsValid(ValidationType.INVALID);
        // project et profil à associer ensuite

        Task task2 = new Task();
        task2.setTaskName("Intégrer Swagger");
        task2.setCoins(10);
        task2.setStatus(Status.IN_PROGRESS);
        task2.setIsValid(ValidationType.VALID);

        List<Task> taskList = List.of(task1, task2);

        // Créer le projet
        Project project = new Project();
        project.setTitle("Plateforme de collaboration");
        project.setDescription("Application web pour permettre aux étudiants de travailler sur des projets.");
        project.setDomaine(Domain.AGRITECH);
        project.setSpecification("Le projet doit contenir une API REST sécurisée, Swagger, JWT et tests unitaires.");
        project.setAuthor(author);
        //project.setManager(manager);
        project.setStatus(Status.IN_PROGRESS);
        project.setLevel(Level.INTERMEDIATE);
        project.setGithubLink("https://github.com/team3/collab-platform");
        project.setTasks(taskList);
        project.setComments(commentList);


        return ":) Super job";


    }





}
