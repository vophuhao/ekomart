package vn.iotstar.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="review")
public class Review implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int rating; // Đánh giá từ 1 đến 5
    private String comment;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime date; // Thêm ngày giờ

    @PrePersist
    public void onCreate() {
        if (date == null) {
            date = LocalDateTime.now(); // Gán ngày giờ hiện tại khi tạo mới
        }
    }
    
    @Transient
    private String formattedDateString;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo user;
}
