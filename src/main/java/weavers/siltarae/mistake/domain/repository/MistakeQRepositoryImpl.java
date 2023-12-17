package weavers.siltarae.mistake.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import weavers.siltarae.like.domain.QLikes;
import weavers.siltarae.mistake.domain.Mistake;
import weavers.siltarae.mistake.domain.QMistake;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MistakeQRepositoryImpl implements MistakeQRepository {

    private final JPAQueryFactory queryFactory;
    private final QMistake mistake = QMistake.mistake;
    private final QLikes likes = QLikes.likes;

    @Override
    public Page<Mistake> findMistakesSortedByLikes(Pageable pageable) {
        List<Mistake> results = queryFactory.selectFrom(mistake)
                .leftJoin(mistake.likes, likes)
                .groupBy(mistake)
                .orderBy(likes.count().desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(mistake)
                .fetch().size();

        return new PageImpl<>(results, pageable, total);
    }
}
