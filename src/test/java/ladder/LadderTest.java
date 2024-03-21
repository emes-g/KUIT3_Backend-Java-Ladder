package ladder;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LadderTest {

    @Test
    void 사다리_생성_확인() {
        //given
        NaturalNumber numberOfRows = NaturalNumber.of(3);
        NaturalNumber numberOfPerson = NaturalNumber.of(4);

        //when
        Ladder ladder = new Ladder(numberOfRows, numberOfPerson);

        //then
        assertNotNull(ladder);
    }

    // 테스트를 위해 준비할 과정이 없으면 given 생략해도 되는지?
    @Test
    void 사다리_시작위치_예외_처리() {
        //when
        // 가능한 사다리 시작위치는 0 ~ 3
        NaturalNumber numberOfPerson = NaturalNumber.of(4);

        //then
        assertThrows(IllegalArgumentException.class, () -> Position.of(5, numberOfPerson));
    }

    @Test
    void 사다리_결과_확인() {
        //given
        NaturalNumber numberOfRows = NaturalNumber.of(3);
        NaturalNumber numberOfPerson = NaturalNumber.of(4);
        Ladder ladder = new Ladder(numberOfRows, numberOfPerson);
        ladder.drawLine(Position.of(0, numberOfRows), Position.of(0, numberOfPerson));
        ladder.drawLine(Position.of(1, numberOfRows), Position.of(1, numberOfPerson));
        ladder.drawLine(Position.of(2, numberOfRows), Position.of(0, numberOfPerson));
        ladder.drawLine(Position.of(2, numberOfRows), Position.of(2, numberOfPerson));
        LadderRunner ladderRunner = new LadderRunner(ladder);

        //when
        Position position = Position.of(0, numberOfPerson);
        Position resultPosition = ladderRunner.run(position);
        //then
        assertEquals(3, resultPosition.get());

        //when
        position = position.next();
        resultPosition = ladderRunner.run(position);
        //then
        assertEquals(1, resultPosition.get());

        //when
        position = position.next();
        resultPosition = ladderRunner.run(position);
        //then
        assertEquals(0, resultPosition.get());

        //when
        position = position.next();
        resultPosition = ladderRunner.run(position);
        //then
        assertEquals(2, resultPosition.get());
    }

    @Test
    void 사다리_출력하기() {
        //given
        NaturalNumber numberOfRows = NaturalNumber.of(3);
        NaturalNumber numberOfPerson = NaturalNumber.of(4);
        Ladder ladder = new Ladder(numberOfRows, numberOfPerson);
        ladder.drawLine(Position.of(0, numberOfRows), Position.of(0, numberOfPerson));
        ladder.drawLine(Position.of(1, numberOfRows), Position.of(1, numberOfPerson));
        ladder.drawLine(Position.of(2, numberOfRows), Position.of(0, numberOfPerson));
        ladder.drawLine(Position.of(2, numberOfRows), Position.of(2, numberOfPerson));

        //when
        String ladderResult = ladder.printLadder();

        //then
        String expectedResult = "1 -1 0 0 \n0 1 -1 0 \n1 -1 1 -1 \n";
        assertEquals(expectedResult, ladderResult);
    }

    @Test
    void 임의_사다리_정상_생성_팩토리_없이() {
        //given
        RandomLadderCreator randomLadderCreator = new RandomLadderCreator();
        Ladder ladder = randomLadderCreator.createLadder(NaturalNumber.of(3), NaturalNumber.of(4));

        //when
        String ladderResult = ladder.printLadder();

        //then
        assertEquals(6, ladderResult.chars()
                .filter(c -> c == '0')
                .count());
    }

    @Test
    void 임의_사다리_정상_생성_팩토리로() {
        //given
        LadderGame ladderGame = LadderGameFactory.createRandomLadderGame(NaturalNumber.of(3), NaturalNumber.of(4));

        //when
        String ladderResult = ladderGame.printLadder();

        //then
        assertEquals(6, ladderResult.chars()
                .filter(c -> c == '0')
                .count());
    }

    //    @RepeatedTest(10)
    @Test
    void 사다리_게임_결과_한_명_정상_출력() {
        //given
        NaturalNumber numberOfRows = NaturalNumber.of(3);
        NaturalNumber numberOfPerson = NaturalNumber.of(4);
        LadderGame ladderGame = LadderGameFactory.createRandomLadderGame(numberOfRows, numberOfPerson);
        Position position = Position.of(2, numberOfPerson);

        //when
        Position onePlayerResult = ladderGame.run(position);

        //then
        double center = (double) (numberOfPerson.get() - 1) / 2;
        assertEquals(center, onePlayerResult.get(), center);
    }

    @Test
    void 사다리_게임_결과_모두_정상_출력() {
        //given
        NaturalNumber numberOfRows = NaturalNumber.of(3);
        NaturalNumber numberOfPerson = NaturalNumber.of(4);
        LadderGame ladderGame = LadderGameFactory.createRandomLadderGame(numberOfRows, numberOfPerson);

        //when
        ladderGame.runAllPlayer();

        //then
        String gameResult = ladderGame.printGameResult();
        assertEquals(2, gameResult.chars()
                .filter(c -> c == '0')
                .count());
        assertEquals(2, gameResult.chars()
                .filter(c -> c == '1')
                .count());
        assertEquals(2, gameResult.chars()
                .filter(c -> c == '2')
                .count());
        assertEquals(2, gameResult.chars()
                .filter(c -> c == '3')
                .count());
    }
}
