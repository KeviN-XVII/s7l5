package kevinquarta.s7l5.controllers;

import kevinquarta.s7l5.entities.Utente;
import kevinquarta.s7l5.exceptions.ValidationException;
import kevinquarta.s7l5.payloads.LoginDTO;
import kevinquarta.s7l5.payloads.ResponseLoginDTO;
import kevinquarta.s7l5.payloads.UtenteDTO;
import kevinquarta.s7l5.services.AuthService;
import kevinquarta.s7l5.services.UtentiService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UtentiService utentiService;

    public AuthController(AuthService authService, UtentiService utentiService) {
        this.authService = authService;
        this.utentiService = utentiService;
    }

    @PostMapping("/login")
    public ResponseLoginDTO login (@RequestBody LoginDTO body) {
        return new ResponseLoginDTO(this.authService.CheckCredentialsAndGenerateToken(body));
    }

//  ------CREO NUOVO UTENTE
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Utente saveUtente(@RequestBody @Validated UtenteDTO payload, BindingResult validationResult){
        if(validationResult.hasErrors()){
            List<String> errorsList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errorsList);
        } else {
            return this.utentiService.saveUtente(payload);
        }
    }







}
