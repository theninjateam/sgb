package sgb.domain;

import java.util.Calendar;
import java.util.List;

public class Requisicao
{
    private Users user;
    private List<Emprestimo> pedidos;
    private String dataentrada;

    public Requisicao(Users user, List<Emprestimo> pedidos) {
        this.user = user;
        this.pedidos = pedidos;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public List<Emprestimo> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Emprestimo> pedidos) {
        this.pedidos = pedidos;
    }

    public String getDataentrada() {
        return dataentrada;
    }

    public void setDataentrada(String dataentrada) {
        this.dataentrada = dataentrada;
    }
}
