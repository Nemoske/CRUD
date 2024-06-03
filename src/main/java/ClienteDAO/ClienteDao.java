package ClienteDAO;

import ClienteDTO.ClienteDto;
import Conexao.ConexaoDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//METODOS STATICOS
    //METODO CREATE
public class ClienteDao {
    public static void cadastrarCliente(Scanner scanf){
        //PEDINDO INFORMAÇÂO
        System.out.println("Digite o nome do cliente:");
        String nomeCliente = scanf.next();
        System.out.println("Digite a data do serviço:");
        String dataMarcada = scanf.next();
        System.out.println("Digite o horário do serviço");
        String horario = scanf.next();
        System.out.println("Valor do serviço:");
        double valor = scanf.nextDouble();
        //COMANDO SQL
        String sql = "insert into cliente (nome_cliente,data_marcada,horario,valor) values (?,?,?,?)";
        //EXECUTAR
        PreparedStatement ps = null;
        try {
            ps = ConexaoDB.getConexaoDB().prepareStatement(sql);
            ps.setString(1,nomeCliente);
            ps.setString(2,dataMarcada);
            ps.setString(3,horario);
            ps.setDouble(4,valor);
            ps.execute();
            ps.close();
        } catch (SQLException erro) {
            erro.printStackTrace();
            System.out.println("Erro ao cadastrar cliente"+erro);
        }
    }
    //METODO READ
    public static List<ClienteDto> getclientes(){
        //COMANDO SQL
        String sql = "select * from cliente";
        //Lista para armazenar as informações
        List<ClienteDto> clientes = new ArrayList<ClienteDto>();
        Connection conn = null;
        PreparedStatement pstn = null;
        ResultSet rset = null;

        try{
            //Criar conexão com banco
            conn = ConexaoDB.getConexaoDB();
            //Criar a classe para executar a query
            pstn = conn.prepareStatement(sql);
            //Executar query
            rset = pstn.executeQuery();
            while(rset.next()){
                ClienteDto cliente = new ClienteDto();
                //PEGANDO ID:
                cliente.setCd_cliente(rset.getInt("cd_cliente"));
                //PEGANOO NOME:
                cliente.setNomeCliente(rset.getString("nome_cliente"));
                //PEGANDO DATA
                cliente.setDataMarcada(rset.getString("data_marcada"));
                //PEGANDO HORARIO
                cliente.setHorario(rset.getString("horario"));
                //PEGANDO VALOR
                cliente.setValor(rset.getDouble("valor"));
                //ADICIONANDO INFORMAÇÔES A LISTA
                clientes.add(cliente);
            }
                //EXIBIR AS INFORMAÇÔES
            for (ClienteDto listarCliente : clientes){
                System.out.println("ID: " + listarCliente.getCd_cliente());
                System.out.println("Nome: " + listarCliente.getNomeCliente());
                System.out.println("Data: " + listarCliente.getDataMarcada());
                System.out.println("Horário " + listarCliente.getHorario());
                System.out.println("Valor R$: " + listarCliente.getValor());
                System.out.println("");
            }
        }catch (SQLException erro){
        erro.printStackTrace();
            System.out.println("Erro, ao exibir informações: "+erro);
        }
        return clientes;
    }
    //METODO UPDATE
    public static void atualizarCliente(Scanner scanf){
        //BUSCANDO CLIENTE ATRAVÉS DO ID
        System.out.println("Digite o ID do cliente:");
        int cd_cliente = scanf.nextInt();
        //EDITANDO INFORMAÇÔES
        System.out.println("Digite o nome do cliente:");
        String nomeCliente = scanf.next();
        scanf.nextLine();
        System.out.println("Digite o data do serviço:");
        String dataMarcada = scanf.next();
        System.out.println("Digite o horário do serviço");
        String horario = scanf.next();
        System.out.println("Valor do serviço:");
        double valor = scanf.nextDouble();
        //COMANDO SQL
        String sql = "update cliente set nome_cliente = ?, data_marcada = ?, horario = ?, valor = ? "+
                "where cd_cliente = ?";
        //REALIZANDO CONEXÂO
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            //Criar conexão com banco
           conn = ConexaoDB.getConexaoDB();
            //Criar a classe para executar a query
           pstm = (PreparedStatement) conn.prepareStatement(sql);
           //Adicionar os valores para atualizar
           pstm.setString(1,nomeCliente);
           pstm.setString(2,dataMarcada);
           pstm.setString(3,horario);
           pstm.setDouble(4,valor);
           pstm.setInt(5,cd_cliente);
            //Executar query
           pstm.execute();

        }catch (SQLException erro){
            erro.printStackTrace();
            System.out.println("Erro, ao atualizar: "+erro);
        }
    }
    //METODO DELETE
    public static boolean deleteCliente(Scanner scanf){
        //BUSCANDO CLIENTE
        System.out.println("Digite o ID do cliente para deletar:");
        int id = scanf.nextInt();
        //COMANDO SQL
        String sql= "delete from cliente where cd_cliente = ?";
        Connection conn = null;
        PreparedStatement pstm = null;
        boolean delete = false;
        try {
            //Criar conexão com banco
            conn = ConexaoDB.getConexaoDB();
            //Criar a classe para executar a query
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.setInt(1,id);
            //Executar query
            int linhasAfetadas = pstm.executeUpdate();
             delete = linhasAfetadas > 0;
             //Caso o ID exista:
            if (delete) {
                System.out.println("Cliente deletado com sucesso!");
            }
            //Caso o ID não exista:
            else {
                System.out.println("Erro ao deletar!");
            }
        }catch (SQLException erro){
        erro.printStackTrace();
            System.out.println("Erro, ao deletar: "+erro);
        }
        return delete;
    }
    //METODO LUCRO
    public static List<ClienteDto> lucro(){
        //COMANDO SQL
        String sql = "select sum(valor) as valor from cliente";
        //Lista para armazenar as informações
        List<ClienteDto> lucroMensal = new ArrayList<ClienteDto>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rset = null;
        try {
            //Criar conexão com banco
            conn = ConexaoDB.getConexaoDB();
            //Criar a classe para executar a query
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            //Executar query
            rset = pstm.executeQuery();
            //PEGANDO VALOR e ADICIONANDO
            while (rset.next()){
                ClienteDto lucro = new ClienteDto();
                lucro.setValor(rset.getDouble("valor"));
                lucroMensal.add(lucro);
            }
            //EXIBIR A INFORMAÇÂO
            for(ClienteDto listarLucro : lucroMensal){
                System.out.println("Valor R$: " + listarLucro.getValor());
            }
        }catch (SQLException erro){
            erro.printStackTrace();
            System.out.println("Erro, ao puxar o lucro: "+erro);
        }
        return lucroMensal;
    }


}
