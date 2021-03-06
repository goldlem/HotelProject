package by.urbel.hotel.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class RoomCategory extends Entity {
    private String categoryName;
    private BigDecimal roomPrice;
    private int bedsCount;
    private String descriptionRu;
    private String descriptionEn;
    private List<String> photoPaths;

    public RoomCategory() {
    }

    public RoomCategory(String categoryName, BigDecimal roomPrice, int bedsCount) {
        this.categoryName = categoryName;
        this.roomPrice = roomPrice;
        this.bedsCount = bedsCount;
    }

    public RoomCategory(String categoryName, BigDecimal roomPrice, int bedsCount, String descriptionRu, String descriptionEn, List<String> photoPaths) {
        this.categoryName = categoryName;
        this.roomPrice = roomPrice;
        this.bedsCount = bedsCount;
        this.descriptionRu = descriptionRu;
        this.descriptionEn = descriptionEn;
        this.photoPaths = photoPaths;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public BigDecimal getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(BigDecimal roomPrice) {
        this.roomPrice = roomPrice;
    }

    public int getBedsCount() {
        return bedsCount;
    }

    public void setBedsCount(int bedsCount) {
        this.bedsCount = bedsCount;
    }

    public List<String> getPhotoPaths() {
        return photoPaths;
    }

    public void setPhotoPaths(List<String> photoPaths) {
        this.photoPaths = photoPaths;
    }

    public String getDescriptionRu() {
        return descriptionRu;
    }

    public void setDescriptionRu(String descriptionRu) {
        this.descriptionRu = descriptionRu;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomCategory category = (RoomCategory) o;
        return bedsCount == category.bedsCount && Objects.equals(categoryName, category.categoryName)
                && Objects.equals(roomPrice, category.roomPrice) && Objects.equals(descriptionRu, category.descriptionRu)
                && Objects.equals(descriptionEn, category.descriptionEn) && Objects.equals(photoPaths, category.photoPaths);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryName, roomPrice, bedsCount, descriptionRu, descriptionEn, photoPaths);
    }
}
