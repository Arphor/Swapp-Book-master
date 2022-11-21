package com.swapp.swapp.service;

import java.time.Year;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.swapp.swapp.GoogleBooksAPI.LivroRequest;
import com.swapp.swapp.model.AutorModel;
import com.swapp.swapp.model.Books;
import com.swapp.swapp.model.CategoriaModel;
import com.swapp.swapp.model.EditoraModel;
import com.swapp.swapp.model.LinguaModel;
import com.swapp.swapp.model.Users;
import com.swapp.swapp.repository.BooksRepository;

@Service
public class BooksService{

    @Autowired
    private BooksRepository books_repository;

    @Autowired 
    EditoraService editoraService;

    @Autowired
    AutorService autorService;

    @Autowired
    LinguaService linguaService;

    @Autowired
    CategoriaService categoriaService;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Books findByIsbn(String isbn){
        Books book = new Books();
        book.setIsbn(isbn);

        LivroRequest req = new LivroRequest();
        JsonNode json = req.getBookDetails(isbn);

        JsonNode item = json.get("items");
        if (item == null){
            return book;
        }
        JsonNode livro = item.get(0);

        if (livro == null){
            return book;
        }

        String titulo = "";
        JsonNode j_titulo = livro.at("/volumeInfo/title");
        if (j_titulo != null){
            titulo = j_titulo.asText();
        }

        String author = "";
        JsonNode j_author = livro.at("/volumeInfo/authors");
        if (j_author != null){
            author = j_author.get(0).asText();
        }

        String publisherdate = "";
        JsonNode j_publisherdate = livro.at("/volumeInfo/publishedDate");
        if (j_publisherdate != null){
            publisherdate = j_publisherdate.asText();
        }

        String description = "";
        JsonNode j_description = livro.at("/volumeInfo/description");
        if (j_description != null){
            description = j_description.asText();
        }
        //livro.get("volumeInfo").get("description").asText();
        book.setDescricao(description);
        book.setPublicacao(publisherdate);
        book.setTitulo(titulo);

        String categories = "";
        JsonNode j_categories = livro.at("/volumeInfo/categories");
        if (j_categories != null){
            categories = j_categories.get(0).asText();
        }

        CategoriaModel m = categoriaService.CategoriaFactory(categories);
        m.getBookslist().add(book);
        book.setCategoria(m);

        String language = "";
        JsonNode j_language = livro.at("/volumeInfo/language");
        if (j_language != null){
            language = j_language.asText();
        }
        Locale loc = new Locale(language);
        String full_lan = loc.getDisplayLanguage();
        LinguaModel l = linguaService.LinguaFactory(full_lan);


        l.getBookslist().add(book);
        book.setLingua(l);


        AutorModel a = autorService.AutorFactory(author);
        a.getBookslist().add(book);
        book.setAutor(a);
        
        String publisher = "";
        JsonNode j_publisher = livro.at("/volumeInfo/publisher");
        if (j_publisher != null){
            publisher = j_publisher.asText();
        }

        EditoraModel e = editoraService.EditoraFactory(publisher);

        e.getBookslist().add(book);
        book.setEditora(e);

        return book;
    }

    public Books registerBook(Users user, String autor, String titulo, String isbn, String publicacao, String lingua, String categoria,
                                String editora){
        /*if(titulo != null && publicacao != null && lingua != null){
            Books book = new Books();

            LinguaService l = new LinguaService(null);
            LinguaModel lin = l.LinguaFactory(lingua);

            lin.setBookslist(List.of(book));
            book.setLingua(lin);


            CategoriaService cat = new CategoriaService(null);
            CategoriaModel c = cat.CategoriaFactory(categoria);

            c.setBookslist(List.of(book));
            book.setCategoria(c);


            EditoraService edi = new EditoraService();
            EditoraModel e = edi.EditoraFactory(editora);

            e.setBookslist(List.of(book));
            book.setEditora(e);

            user.setBookslist(List.of(book));
            book.setUser(user);

            book.setTitulo(titulo);
            book.setPublicacao(publicacao);
            book.setIsbn(isbn);
            return books_repository.save(book);
        }else{*/
            return null;
        //}
    }   
}
