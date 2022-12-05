package uz.aknb.security.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.aknb.db.entity.EntUser;
import uz.aknb.db.mapper.UserMapper;
import uz.aknb.db.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.Collection;

import static java.util.Collections.singletonList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    public UserDetailsServiceImpl(UserRepository userRepository,
                                  UserMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        EntUser user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found with this email: " + email)
        );

        return mapper.toUserDetails(user);
    }
}
