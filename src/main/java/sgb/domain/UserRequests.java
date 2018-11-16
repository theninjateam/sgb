package sgb.domain;

import java.util.List;

public class UserRequests
{
    private Users user;
    private List<Emprestimo> pedidos;

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
}
