package id.holigo.services.holigocouponservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import id.holigo.services.holigocouponservice.domain.Language;

public interface LanguageRepository extends JpaRepository<Language, Long> {
    Language findByMessageKeyAndLocale(String messageKey, String locale);

}
