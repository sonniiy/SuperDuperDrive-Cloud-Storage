package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper {

    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<Note> getAllNotes(int userId);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int addNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    void delete(Integer noteid);

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteId} AND userid = #{userId}")
    File getFileName(Integer noteId, Integer userId);

    @Update("UPDATE NOTES SET notetitle = #{notetitle}, notedescription = #{notedescription}  WHERE noteid = #{noteid} AND userid = #{userId}")
    boolean update(Note note);

}
