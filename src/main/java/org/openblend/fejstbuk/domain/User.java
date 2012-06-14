package org.openblend.fejstbuk.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
@Entity
public class User extends AbstractEntity {
    private String name;
    private String surname;
    private Date birth;
    private Gender gender;
    private String location;
    private User relationship;
    private Set<User> friends;
    private Set<Linked> posts;
    private Set<Comment> comments;
    private Set<Like> likes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @ManyToOne
    public User getRelationship() {
        return relationship;
    }

    public void setRelationship(User relationship) {
        this.relationship = relationship;
    }

    @ManyToMany // TODO - is this ok?
    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    @OneToMany(mappedBy = "user")
    public Set<Linked> getPosts() {
        return posts;
    }

    public void setPosts(Set<Linked> posts) {
        this.posts = posts;
    }

    @OneToMany(mappedBy = "user")
    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    @OneToMany(mappedBy = "user")
    public Set<Like> getLikes() {
        return likes;
    }

    public void setLikes(Set<Like> likes) {
        this.likes = likes;
    }
}
