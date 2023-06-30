package project.seatsence.src.admin.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name="admin_member_authority")
public class AdminMemberAuthority {

    @Id
    private Long id;


}
