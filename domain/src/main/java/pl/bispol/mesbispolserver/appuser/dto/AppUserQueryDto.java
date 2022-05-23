package pl.bispol.mesbispolserver.appuser.dto;

public interface AppUserQueryDto {
    long getId();

    String getUsername();

    String getEmail();

    String getAppUserRole();

    boolean isEnabled();

}
