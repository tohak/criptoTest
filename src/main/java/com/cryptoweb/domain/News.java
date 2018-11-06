package com.cryptoweb.domain;


import com.cryptoweb.domain.enums.CategoryEnamNews;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "news_tbl")
@AllArgsConstructor
@NoArgsConstructor
public class News extends BaseEntity {
    public static final int LENGTH = 2000;
    public static final boolean BUN_NULL = false;

    @NotBlank(message = "Input name news")
    @Column(name = "news_name", nullable = BUN_NULL, length = LENGTH)
    private String nameNews;

    @NotBlank(message = "Input part text")
    @Length(max = LENGTH, message = "limit light message 2000 ")
    @Column(name = "part", nullable = BUN_NULL, length = LENGTH)
    private String partTextNews;

    @NotBlank(message = "Input text news")
    @Column(name = "text_news", nullable = BUN_NULL)
    private String textNews;

    @Length(max = LENGTH, message = "limit light tag 2000")
    @Column(name = "teg_news", nullable = BUN_NULL, length = LENGTH)
    private String tagNews;

    @Enumerated(EnumType.STRING)
    @Column(name = "category_enum")
    private CategoryEnamNews categoryEnum;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    @Column(updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;

    private String filename;


}
