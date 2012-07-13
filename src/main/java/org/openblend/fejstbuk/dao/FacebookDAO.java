package org.openblend.fejstbuk.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.sql.rowset.serial.SerialBlob;

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
@SuppressWarnings("unchecked")
@Stateless
public class FacebookDAO {
    @Inject
    private EntityManager em;


    public void addFriend(User owner, User friend) {
        owner = em.merge(owner);
        Set<User> friends = owner.getFriends();
        if (friends == null) {
            friends = new HashSet<User>();
            owner.setFriends(friends);
        }
        friends.add(friend);
    }

    public void removeFriend(User owner, User friend) {
        owner = em.merge(owner);
        Set<User> friends = owner.getFriends();
        if (friends != null) {
            friends.remove(friend);
        }
    }

    public List<Linked> wall(User owner, int offset, int size) {
        Query query = em.createQuery("select l from Linked l where l.user = :owner order by l.timestamp desc");
        query.setParameter("owner", owner);
        query.setFirstResult(offset);
        query.setMaxResults(size);
        return query.getResultList();
    }

    protected void addLinked(User owner, Linked linked) {
        Set<Linked> posts = owner.getPosts();
        if (posts == null) {
            posts = new HashSet<Linked>();
            owner.setPosts(posts);
        }
        posts.add(linked);
    }

    public Status addStatus(User owner, String status) {
        Status st = new Status();
        st.setStatus(status);
        st.setUser(owner);
        st.setTimestamp(new Date());
        em.persist(st);
        addLinked(owner, st);
        return st;
    }

    public Image addImage(User owner, byte[] image) {
        try {
            Image im = new Image();
            im.setImage(new SerialBlob(image));
            im.setUser(owner);
            im.setTimestamp(new Date());
            em.persist(im);
            addLinked(owner, im);
            return im;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Question addQuestion(User owner, String question) {
        Question q = new Question();
        q.setQuestion(question);
        q.setUser(owner);
        q.setTimestamp(new Date());
        em.persist(q);
        addLinked(owner, q);
        return q;
    }

    public Comment addComment(Linked linked, User user, String text) {
        Comment comment = new Comment();
        comment.setText(text);
        comment.setUser(user);
        comment.setTimestamp(new Date());
        em.persist(comment);
        // linked comments
        Set<Comment> comments = linked.getComments();
        if (comments == null) {
            comments = new HashSet<Comment>();
            linked.setComments(comments);
        }
        comments.add(comment);
        // user comments
        comments = user.getComments();
        if (comments == null) {
            comments = new HashSet<Comment>();
            user.setComments(comments);
        }
        comments.add(comment);
        return comment;
    }

    public void removePost(Post post) {
        em.remove(post);
    }

    public Like like(User user, Post post) {
        Like like = new Like();
        like.setPost(post);
        like.setUser(user);
        like.setTimestamp(new Date());
        em.persist(like);
        // post likes
        Set<Like> likes = post.getLikes();
        if (likes == null) {
            likes = new HashSet<Like>();
            post.setLikes(likes);
        }
        likes.add(like);
        // user likes
        likes = user.getLikes();
        if (likes == null) {
            likes = new HashSet<Like>();
            user.setLikes(likes);
        }
        likes.add(like);
        return like;
    }

    public void unlike(Like like) {
        em.remove(like);
        Post post = like.getPost();
        if (post != null) {
            Set<Like> likes = post.getLikes();
            if (likes != null) {
                likes.remove(like);
            }
        }
        User user = like.getUser();
        if (user != null) {
            Set<Like> likes = user.getLikes();
            if (likes != null) {
                likes.remove(like);
            }
        }
    }

    public User findUser(String username) {
        TypedQuery<User> query = em.createQuery("select u from User u where u.email = :u", User.class);
        query.setParameter("u", username);
        query.setMaxResults(1);
        return query.getResultList().isEmpty()?null:query.getResultList().get(0);
    }

    public boolean createUser(User user) {
        if (findUser(user.getEmail()) == null) {
            em.persist(user);
            return true;
        }

        return false;
    }

}
