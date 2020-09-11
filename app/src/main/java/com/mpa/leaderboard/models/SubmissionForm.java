package com.mpa.leaderboard.models;


public class SubmissionForm {
    private String email;
    private String name;
    private String lastName;
    private String githubLink;

    public SubmissionForm() {
    }

    public SubmissionForm(String email, String name, String lastName, String githubLink) {
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.githubLink = githubLink;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGithubLink() {
        return githubLink;
    }

    public void setGithubLink(String githubLink) {
        this.githubLink = githubLink;
    }
}
