import java.awt.List;
import java.sql.Date;
import java.util.ArrayList;

import java_cup.runtime.ComplexSymbolFactory.Location;

import javax.xml.ws.handler.MessageContext;

import org.omg.CORBA.PRIVATE_MEMBER;

import com.thoughtworks.xstream.annotations.XStreamOmitField;






public class UserInfo {

	private  String Location;
	private String id;
	private String bio;
	@XStreamOmitField
	private ArrayList <Post> PostList;
	@XStreamOmitField
	private ArrayList <Comment> CommentList;
	private ArrayList <DatesInfo> UserCalendr;
	
	
	
	
	public UserInfo()
	{

	}

	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public ArrayList <Post> getPostList() {
		return PostList;
	}

	public void setPostList(ArrayList <Post> postList) {
		PostList = postList;
	}

	public ArrayList <DatesInfo> getUserCalendr() {
		return UserCalendr;
	}

	public void setUserCalendr(ArrayList <DatesInfo> userCalendr) {
		UserCalendr = userCalendr;
	}

	public ArrayList <Comment> getCommentList() {
		return CommentList;
	}

	public void setCommentList(ArrayList <Comment> commentList) {
		CommentList = commentList;
	}
	
	
}


class UserInfoList
{
	private ArrayList<UserInfo> UserInfoCatalog;
	
	public UserInfoList()
	{
		
	}

	public ArrayList<UserInfo> getUserInfoCatalog() {
		return UserInfoCatalog;
	}

	public void setUserInfoCatalog(ArrayList<UserInfo> userInfoCatalog) {
		UserInfoCatalog = userInfoCatalog;
	}
	}

class Post
{
	private String date;
	
	private String postId;
	
	private  String messageString;

	private double mes_class;
	
	public String getDate() {
		return date;
	}

	public void setDate(String string) {
		this.date = string;
	}

	public String getMessageString() {
		return messageString;
	}

	public void setMessageString(String messageString) {
		this.messageString = messageString;
	}

	public double getMes_class() {
		return mes_class;
	}

	public void setMes_class(double mes_class) {
		this.mes_class = mes_class;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}
	
}


class Comment
{
private String date;
	
	private String relatedpostId;
	
	private  String messageString;

	private double mes_class;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getRelatedpostId() {
		return relatedpostId;
	}

	public void setRelatedpostId(String relatedpostId) {
		this.relatedpostId = relatedpostId;
	}

	public String getMessageString() {
		return messageString;
	}

	public void setMessageString(String messageString) {
		this.messageString = messageString;
	}

	public double getMes_class() {
		return mes_class;
	}

	public void setMes_class(double mes_class) {
		this.mes_class = mes_class;
	}
}
