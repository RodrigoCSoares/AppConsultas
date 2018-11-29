package com.example.rodrigosoares.appconsultas.database;

public class Consulta {

    private Integer id;
    private String data_agenda;
    private String observacao;
    private String local;

    public Consulta(Integer id, String data_agenda, String observacao, String local) throws Exception {
        try {
            this.id = id;
            this.data_agenda = data_agenda;
            this.observacao = observacao;
            this.local = local;
        } catch (Exception erro) {
            throw new Exception("valores não fornecidos corretamente ");
        }
    }

    public void setID(Integer id) throws Exception {
        if (id == null)
            throw new Exception("id nula");
        this.id = id;
    }

    public void SetData_agenda(String data_agenda) {
        this.data_agenda = data_agenda;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Integer getID() {
        return this.id;
    }

    public String getData_agenda() {
        return this.data_agenda;
    }

    public String getObservacao() {
        return this.observacao;
    }

    public String getLocal() {
        return this.local;
    }
}
