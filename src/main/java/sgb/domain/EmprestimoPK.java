package sgb.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class EmprestimoPK implements Serializable {
    private int userId;
    private String cota;

    @Column(name = "user_id", nullable = false)
    @Id
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Column(name = "cota", nullable = false, length = 255)
    @Id
    public String getCota() {
        return cota;
    }

    public void setCota(String cota) {
        this.cota = cota;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmprestimoPK that = (EmprestimoPK) o;
        return userId == that.userId &&
                Objects.equals(cota, that.cota);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, cota);
    }
}
