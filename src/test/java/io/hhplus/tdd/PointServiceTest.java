package io.hhplus.tdd;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.PointService;
import io.hhplus.tdd.point.UserPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PointServiceTest {

    UserPointTable userPointTable;
    PointHistoryTable historyTable;
    PointService pointService;

    @BeforeEach
    void setUp() {
        // given
        userPointTable = new UserPointTable();
        historyTable = new PointHistoryTable();

        // 초기 데이터 세팅
        userPointTable.insertOrUpdate(1L, 500L);

        // Service에 의존성 주입
        pointService = new PointService(userPointTable, historyTable);
    }

    @Test
    @DisplayName("유저 포인트를 반환한다.")
    void givenId_whenGetPoint_thenReturnPoint() {

        // given
        long id = 1L;

        // when
        UserPoint result = pointService.getPoint(id);

        // then
        //id를 확인하고 포인트가 같은지 확인한다
        assertThat(result.id()).isEqualTo(id);
        assertThat(result.point()).isEqualTo(500L);
    }

    @Test
    @DisplayName("유저 충전/이용 내역을 조회한다.")
    void givenId_whenGetHistory_thenReturnHistory() {

        long id = 1L;

        List<PointHistory> result = pointService.getPointHistory(id);

        // 충전과 사용 테스트 코드를 거치고 기록이 2개가 있는지 확인한다.
        assertThat(result).hasSize(2);
    }

    @Test
    @DisplayName("유저 포인트를 충전한다.")
    void givenIdAndAmount_whenChargeUserPoint_thenReturnUserPoint() {

        long id = 1L;
        UserPoint userPoint = pointService.chargePoint(id, 500L);

        // 사용자 아이디가 같은지 확인하고 500+500 충전되었는지 확인한다
        assertThat(userPoint.id()).isEqualTo(id);
        assertThat(userPoint.point()).isEqualTo(1000L);
    }

    @Test
    @DisplayName("유저 포인트를 사용한다.")
    void givenIdAndAmount_whenUseUserPoint_thenReturnUserPoint() {

        long id = 1L;
        UserPoint userPoint = pointService.usePoint(id, 200L);

        //사용자 아이디가 같은지 확인하고 500-200 사용되었는지 확인한다
        assertThat(userPoint.id()).isEqualTo(id);
        assertThat(userPoint.point()).isEqualTo(300L);
    }
}
