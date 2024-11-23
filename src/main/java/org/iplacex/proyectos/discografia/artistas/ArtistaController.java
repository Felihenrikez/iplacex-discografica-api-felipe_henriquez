package org.iplacex.proyectos.discografia.artistas;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ArtistaController {
    @Autowired
    private IArtistaRepository artistarepo;


    @PostMapping(
        value = "/artista",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Artista> HandleInsetArtistaRequest(@RequestBody Artista artista){
        Artista temp = artistarepo.insert(artista);
        return new ResponseEntity<>(temp,null,HttpStatus.CREATED);

    }

    @GetMapping(
        value = "/artistas",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Artista>>HandleGetArtistasRequest(){
        List<Artista> artistas =artistarepo.findAll();
        return new ResponseEntity<>(artistas,null,HttpStatus.OK);
    }

    @GetMapping(
        value = "/artista/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE

    )
    public ResponseEntity<Artista> HandleGetArtistaRequest(@PathVariable("id") String id){
        Optional<Artista> temp = artistarepo.findById(id);
        if(!temp.isPresent()){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(temp.get(),null,HttpStatus.OK);
    }

    @PutMapping(
    value = "/artista/{id}",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Artista> HandleUpdateArtistaRequest(
        @PathVariable("id") String id,
        @RequestBody Artista artista
        ){  
            if(!artistarepo.existsById(id)){
                return new ResponseEntity<>(null,null,HttpStatus.NOT_FOUND);
            }
            artista._id =id;
            Artista temp = artistarepo.save(artista);

            return new ResponseEntity<>(temp, null,HttpStatus.OK);

        }

    @DeleteMapping(value = "/artista/{id}")
    public ResponseEntity<Artista> HandleDeleteArtistaRequest(@PathVariable("id") String id){
        if(!artistarepo.existsById(id)){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        Artista temp = artistarepo.findById(id).get();
        artistarepo.deleteById(id);

        return new ResponseEntity<>(temp,null,HttpStatus.OK);
    }

}
