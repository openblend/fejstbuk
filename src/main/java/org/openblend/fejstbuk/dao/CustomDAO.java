package org.openblend.fejstbuk.dao;

import java.util.List;

import javax.ejb.Local;

import org.openblend.fejstbuk.domain.Comment;
import org.openblend.fejstbuk.domain.Image;
import org.openblend.fejstbuk.domain.Like;
import org.openblend.fejstbuk.domain.Linked;
import org.openblend.fejstbuk.domain.Post;
import org.openblend.fejstbuk.domain.Question;
import org.openblend.fejstbuk.domain.Status;
import org.openblend.fejstbuk.domain.User;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
@Local
public interface CustomDAO extends GenericDAO {
    void addFriend(User owner, User friend);

    void removeFriend(User owner, User friend);

    List<Linked> wall(User owner, int offset, int size);

    Status addStatus(User owner, String status);

    Image addImage(User owner, byte[] image);

    Question addQuestion(User owner, String question);

    Comment addComment(Linked linked, User user, String comment);

    void removePost(Post post);

    Like like(User user, Post post);

    void unlike(Like like);

    User findUser(String username);
}
