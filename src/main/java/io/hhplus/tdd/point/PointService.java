package io.hhplus.tdd.point;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PointService {

    private final UserPointTable userPointTable;
    private final PointHistoryTable pointHistoryTable;

    public PointService(UserPointTable userPointTable,
                        PointHistoryTable pointHistoryTable) {
        this.userPointTable = userPointTable;
        this.pointHistoryTable = pointHistoryTable;
    }

    public UserPoint getPoint(Long id){
        return userPointTable.selectById(id);
    }

    public List<PointHistory> getPointHistory(Long id){
        return pointHistoryTable.selectAllByUserId(id);
    }

    public UserPoint chargePoint(Long id, Long amount){

        UserPoint userPoint = userPointTable.selectById(id);
        return userPointTable.insertOrUpdate(id, userPoint.point() +amount);
    }

    public UserPoint usePoint(Long id, Long amount){

        UserPoint userPoint = userPointTable.selectById(id);
        return userPointTable.insertOrUpdate(id, userPoint.point() - amount);
    }
}
