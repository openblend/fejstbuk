package org.openblend.fejstbuk.dao;

import java.util.List;

import javax.ejb.Local;

import org.openblend.fejstbuk.domain.Comment;
import org.openblend.fejstbuk.domain.Image;
import org.openblend.fejstbuk.domain.Like;
import org.openblend.fejstbuk.domain.Post;
import org.openblend.fejstbuk.domain.User;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
@Local
public interface CustomDAO extends GenericDAO {
    void addFriend(User owner, User friend);

    void removeFriend(User owner, User friend);

    List<Post> wall(User owner, int offset, int size);

    Comment addComment(User owner, String text);

    Image addImage(User owner, byte[] image);

    void removePost(Post post);

    Like like(User user, Post post);

    void unlike(Like like);
}
