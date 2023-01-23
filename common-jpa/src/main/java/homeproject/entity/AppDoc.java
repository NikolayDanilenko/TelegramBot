package homeproject.entity;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_document")
public class AppDoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String telegramFiled;
    private String docName;

    @OneToOne
    private BinaryContent binaryContent;
    private String mimeType;
    private Long fileSize;

}
