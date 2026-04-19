import java.util.Dictionary

data class Livro(val titulo: String, val autor: String, val isbn: String, var disponivel: Boolean)

data class Usuario(val nome: String, val id: Int, val livrosEmprestados: MutableList<Livro>)

data class Biblioteca(val acervo: MutableList<Livro>, val usuarios: MutableList<Usuario>)

interface Emprestavel {
    fun emprestar(usuario: Usuario): Boolean

    fun devolver(): Boolean
}

fun mostrarHistoricoEmprestimos(historicoEmprestimos : MutableList<MutableMap<String, Any>>){
    for (emprestimo in historicoEmprestimos){
        for(dado in emprestimo){
            println(dado.toString())
        }
    }
}

fun cadastrarLivro(): Livro {
    println("Título do Livro: ")
    val titulo = leituraSegura()

    println("Autor do Livro: ")
    val autor = leituraSegura()

    var isbn: String
    do {
        println("Isbn do Livro: ")
        isbn = leituraSegura()
    } while (isbn.length != 13)

    var estaDisponivel: Boolean
    do {
        println("Disponibilidade: (1 para DISPONÍVEL/ 2 para INDISPONÍVEL) --> ")
        val disponivel = leituraSegura().toInt()

        if (disponivel == 1) {
            estaDisponivel = true
        } else {
            estaDisponivel = false
        }
    } while (disponivel != 1 && disponivel != 2)

    val livro = Livro(titulo, autor, isbn, estaDisponivel)
    return livro
}

fun leituraSegura() = readLine() ?: error("Valor nulo inválido!")

fun lerUsuario(usuario: MutableList<Usuario>): Usuario {
    println("Digite o NOME do usuário: ")
    val nome = leituraSegura()

    val id = usuario.size + 1

    var quanteLivros = 0

    do {
        println("Quantos livros deseja emprestar? ")
        quanteLivros = leituraSegura().toInt()
    } while (quanteLivros > 3)

    var livrosEmprestados = mutableListOf<Livro>()
    var cont = quanteLivros

    while (cont > 0) {
        val livro = cadastrarLivro()
        livrosEmprestados.add(livro)
        cont -= 1
    }

    val usuario = Usuario(nome, id, livrosEmprestados)
    return usuario
    //println(nome)
    //println(quanteLivros)

//    for (livro in livrosEmprestados) {
//        println(livro.titulo)
//    }

}

fun main() {
    var livros: MutableList<Livro> = mutableListOf(
        Livro("Peter Pan", "JM Berrie", "1234567891234", true),
        Livro("Jogos Vorazes", "JM Berrie", "1174546891639", false),
        Livro("A Metarmofose", "Franz Kafka", "1224543811679", true),
    )

    var usuarios: MutableList<Usuario> = mutableListOf(
        Usuario(
            "Pedro", 1,
            mutableListOf(
                Livro("Peter Pan", "JM Berrie", "1234567891234", true),
                Livro("Jogos Vorazes", "JM Berrie", "1174546891639", false),
                Livro("A Metarmofose", "Franz Kafka", "1224543811679", true)
            )
        )
    )

    var historicoEmprestimos : MutableList<MutableMap<String, Any>> = mutableListOf()

    while (true) {
        println("")
        println("Escolha a ação: ")
        println("0 para SAIR\n1 para ADICIONAR USUÁRIO\n2 para CADASTRAR LIVROS\n3 para DEVOLVER LIVROS\n4 para LISTAR LIVROS DISPONÍVEIS\n5 para BUSCAR por TÍTULO ou AUTOR\n6 para MOSTRAR HISTÓRICO\n--> ")

        var acao = readLine()!!.toInt()

        if (acao == 0) {
            break
        } else if (acao == 1) {
            var usuario = lerUsuario(usuarios)
            usuarios.add(usuario)
            for (livro in usuario.livrosEmprestados) {
                livros.add(livro)
            }

            var listaLivros = mutableListOf<Livro>()
            for (livro in usuario.livrosEmprestados) {
                listaLivros.add(livro)
            }

            var dicionario = mutableMapOf(
                "Usuário" to usuario,
                "Livros" to listaLivros
            )

            historicoEmprestimos.add(
               dicionario
            )

        } else if (acao == 2) {
            val livro = cadastrarLivro()
            livros.add(livro)
        }
        else if (acao == 4) {
            println("Livros disponíveis: ")
            for (livro in livros){
                if (livro.disponivel){
                    println(livro.titulo)
                }
            }
        }
        else if(acao == 5){
            println("Digite o NOME do autor ou TÍTULO do livro: ")
            val pesquisa = leituraSegura()

            for (livro in livros){
                if (livro.titulo == pesquisa || livro.autor == pesquisa){
                    println("Título = ${livro.titulo}")
                    println("Autor = ${livro.autor}")
                    println("Isbn = ${livro.isbn}")
                    if(livro.disponivel){
                        println("Disponível")
                    }
                    else{
                        println("Indisponível")
                    }
                }
            }
        }
        else if (acao == 6){
            mostrarHistoricoEmprestimos(historicoEmprestimos)
        }

        var biblioteca = Biblioteca(livros, usuarios)
        println("Acervo da biblioteca: ")
        for (livro in biblioteca.acervo) {
            println(livro.titulo)
        }
    }
}