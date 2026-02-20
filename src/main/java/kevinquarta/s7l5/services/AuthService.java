package kevinquarta.s7l5.services;

import kevinquarta.s7l5.entities.Utente;
import kevinquarta.s7l5.exceptions.UnauthroizedException;
import kevinquarta.s7l5.payloads.LoginDTO;
import kevinquarta.s7l5.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UtentiService utentiService;
    private final JWTTools jwtTools;
    private final PasswordEncoder bcrypt;

    @Autowired
    public AuthService(UtentiService utentiService, JWTTools jwtTools, PasswordEncoder bcrypt) {
        this.utentiService = utentiService;
        this.jwtTools = jwtTools;
        this.bcrypt = bcrypt;
    }

    public String CheckCredentialsAndGenerateToken(LoginDTO body) {

// ------CONTROLLO CREDENZIALI
        Utente found = this.utentiService.findByEmail(body.email());

        if(bcrypt.matches(body.password(), found.getPassword())) {
// -------SE IL CONTROLLO CREDENZIALI VA OK,GENERA TOKEN E LO RESTITUISCE
            String accessToken= jwtTools.generateToken(found);

            return accessToken;
        }
        else {
            throw new UnauthroizedException("Credenziali Errate");
        }
    }
}
