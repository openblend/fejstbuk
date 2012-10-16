package org.openblend.fejstbuk.ui;

import java.util.List;
import java.util.Set;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

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
@ConversationScoped
@Named("wall")
public class Wall extends AbstractUI {
    public static final int SIZE = 20;

    public List<Linked> getWall(int start) {
        return dao.wall(current, start, SIZE);
    }

    public Status postStatus(String status) {
        return dao.addStatus(current, status);
    }

    public Image postImage(byte[] image) {
        return dao.addImage(current, image);
    }

    public Question postQuestion(String question) {
        return dao.addQuestion(current, question);
    }

    public Comment addComment(long linkedId, String comment) {
        Linked linked = em.find(Linked.class, linkedId);
        if (linked != null) {
            return dao.addComment(linked, current, comment);
        }
        return null;
    }

    public Like addLike(long postId) {
        Post post = em.find(Post.class, postId);
        if (post != null) {
            return dao.like(current, post);
        }
        return null;
    }

    public void removeLike(long likeId) {
        Like like = em.find(Like.class, likeId);
        if (like != null) {
            dao.unlike(like);
        }
    }

    public Set<User> getFriends() {
        return current.getFriends();
    }

    public Set<User> getFriends(long userId) {
        User user = em.find(User.class, userId);
        return user.getFriends();
    }

    @Produces
    @Named
    public List<Status> getWallFeed() {
        return em.createQuery("select a from Status a where a.user.id = :userId order by a.timestamp desc", Status.class)
                .setParameter("userId", current.getId())
                .getResultList();

    }
}
