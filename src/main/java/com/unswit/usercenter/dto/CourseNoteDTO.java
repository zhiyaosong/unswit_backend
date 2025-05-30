package com.unswit.usercenter.dto;

import com.unswit.usercenter.model.domain.Note;

import java.util.List;

public class CourseNoteDTO {
    private Long courseId;

    private String code;

    private String title;

    private Integer category;

    private String toolTip;

    private Integer enrollTerm;

    private List<Note> noteList;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getToolTip() {
        return toolTip;
    }

    public void setToolTip(String toolTip) {
        this.toolTip = toolTip;
    }

    public Integer getEnrollTerm() {
        return enrollTerm;
    }

    public void setEnrollTerm(Integer enrollTerm) {
        this.enrollTerm = enrollTerm;
    }

    public List<Note> getNoteList() {
        return noteList;
    }

    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
    }
}
