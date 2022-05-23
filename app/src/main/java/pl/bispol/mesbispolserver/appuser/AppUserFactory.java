package pl.bispol.mesbispolserver.appuser;

import pl.bispol.mesbispolserver.appuser.dto.AppUserRegisterDto;

class AppUserFactory {
    AppUserFactory() {
    }

    static AppUser toEntity(AppUserRegisterDto appUserRegisterDto) {
        return new AppUser(
                appUserRegisterDto.getUsername(),
                appUserRegisterDto.getPassword(),
                appUserRegisterDto.getEmail()
        );
    }


}
