package model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RepoDTO {
    private int id;
    private Date createdAt;
    private String description;
    private String homepage;
    private String html_url;
    private String language;
    private String name;
    private String owner;
    private String url;
    private String[] topics;
}
