package org.openblend.fejstbuk.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.rowset.serial.SerialBlob;

import org.openblend.fejstbuk.dao.CustomDAO;
import org.openblend.fejstbuk.domain.Comment;
import org.openblend.fejstbuk.domain.Image;
import org.openblend.fejstbuk.domain.Like;
import org.openblend.fejstbuk.domain.Post;
import org.openblend.fejstbuk.domain.User;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
@SuppressWarnings("unchecked")
@Stateless
public class CustomDAOImpl extends AbstractGenericDAO implements CustomDAO {
    @PersistenceContext
    private EntityManager em;

    protected EntityManager getEM() {
        return em;
    }

    public void addFriend(User owner, User friend) {
        owner = merge(owner);
        Set<User> friends = owner.getFriends();
        if (friends == null) {
            friends = new HashSet<User>();
            owner.setFriends(friends);
        }
        friends.add(friend);
    }

    public void removeFriend(User owner, User friend) {
        owner = merge(owner);
        Set<User> friends = owner.getFriends();
        if (friends != null) {
            friends.remove(friend);
        }
    }

    public List<Post> wall(User owner, int offset, int size) {
        Query query = getEM().createQuery("select Post p where p.user = :owner order by p.timestamp desc");
        query.setParameter("owner", owner);
        query.setFirstResult(offset);
        query.setMaxResults(size);
        return query.getResultList();
    }

    public Comment addComment(User owner, String text) {
        Comment comment = new Comment();
        comment.setText(text);
        comment.setUser(owner);
        comment.setTimestamp(new Date());
        save(comment);
        return comment;
    }

    public Image addImage(User owner, byte[] image) {
        try {
            Image im = new Image();
            im.setImage(new SerialBlob(image));
            im.setUser(owner);
            im.setTimestamp(new Date());
            return im;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void removePost(Post post) {
        delete(post);
    }

    public Like like(User user, Post post) {
        Like like = new Like();
        like.setPost(post);
        like.setUser(user);
        like.setTimestamp(new Date());
        return like;
    }

    public void unlike(Like like) {
        delete(like);
    }
}
