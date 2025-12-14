package org.example.ehali.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FavoriId implements Serializable {

    @Column(name = "musteri_id")
    private Long musteriId;

    @Column(name = "hali_id")
    private Long haliId;

    public FavoriId() {
    }

    public FavoriId(Long musteriId, Long haliId) {
        this.musteriId = musteriId;
        this.haliId = haliId;
    }

    public Long getMusteriId() {
        return musteriId;
    }

    public void setMusteriId(Long musteriId) {
        this.musteriId = musteriId;
    }

    public Long getHaliId() {
        return haliId;
    }

    public void setHaliId(Long haliId) {
        this.haliId = haliId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoriId favoriId = (FavoriId) o;
        return Objects.equals(musteriId, favoriId.musteriId) &&
               Objects.equals(haliId, favoriId.haliId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(musteriId, haliId);
    }
}
