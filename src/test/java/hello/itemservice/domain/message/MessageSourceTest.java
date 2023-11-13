package hello.itemservice.domain.message;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class MessageSourceTest {

    @Autowired
    MessageSource messageSource;

    @Test
    @DisplayName("메세지 테스트")
    void helloMessage() {
        String result = messageSource.getMessage("hello", null, null);
        assertThat(result).isEqualTo("안녕");
    }

    @Test
    @DisplayName("없는 값일 경우 테스트 - 예외 발생 시 성공")
    void notFoundMessageCode() {
        assertThatThrownBy(() -> messageSource.getMessage("no_code", null, null))
                .isInstanceOf(NoSuchMessageException.class);

    }

    @Test
    @DisplayName("없는 값일 경우 기본 메세지 테스트")
    void notFoundMessageCodeDefaultMessage() {
        String result = messageSource.getMessage("no_code", null, "기본 메세지", null);
        assertThat(result).isEqualTo("기본 메세지");
    }

    @Test
    @DisplayName("매개변수 사용한 메세지 테스트")
    void argumentMessage() {
        String result = messageSource.getMessage("hello.name", new Object[]{"Spring"}, null);
        assertThat(result).isEqualTo("안녕 Spring");
    }

    @Test
    @DisplayName("국제화 파일 선택 테스트")
    void defaultLang() {
        assertThat(messageSource.getMessage("hello", null, null)).isEqualTo("안녕");
        assertThat(messageSource.getMessage("hello", null, Locale.KOREA)).isEqualTo("안녕");
    }

    @Test
    @DisplayName("국제화 파일 선택 테스트2")
    void enLang() {
        assertThat(messageSource.getMessage("hello", null, Locale.ENGLISH)).isEqualTo("hello");
    }
}
