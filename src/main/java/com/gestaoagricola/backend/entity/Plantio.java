package com.gestaoagricola.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class Plantio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "talhao_id", nullable = false)
    private Talhao talhao;

    @ManyToOne
    @JoinColumn(name = "cultura_id", nullable = false)
    private CulturaAgricola cultura;

    @Column(name = "area_plantada", precision = 12, scale = 2, nullable = false)
    private BigDecimal areaPlantada;

    public BigDecimal getAreaPlantada() {
        return areaPlantada;
    }

    @Column(name = "data_prevista", nullable = false)
    private LocalDate dataPrevista;

    public LocalDate getDataPrevista() {
        return dataPrevista;
    }

    public void setDataPrevista(LocalDate dataPrevista) {
        this.dataPrevista = dataPrevista;
    }

    @Column(name = "data_realizada")
    private LocalDate dataRealizada;

    public LocalDate getDataRealizada() {
        return dataRealizada;
    }

    public void setDataRealizada(LocalDate dataRealizada) {
        this.dataRealizada = dataRealizada;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusPlantio status;

    public StatusPlantio getStatus() {
        return status;
    }

    public void setStatus(StatusPlantio status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Talhao getTalhao() {
        return talhao;
    }

    public void setTalhao(Talhao talhao) {
        this.talhao = talhao;
    }

    public CulturaAgricola getCultura() {
        return cultura;
    }

    public void setCultura(CulturaAgricola cultura) {
        this.cultura = cultura;
    }

    public void setAreaPlantada(BigDecimal areaPlantada) {
        this.areaPlantada = areaPlantada;
    }
}