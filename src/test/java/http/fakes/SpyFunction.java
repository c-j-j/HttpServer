package http.fakes;

import java.util.function.Function;

public class SpyFunction<T, R> implements Function<T, R> {

    private T calledWith;
    private R stubResponse;

    public SpyFunction(R stubResponse) {

        this.stubResponse = stubResponse;
    }

    @Override
    public R apply(T o) {
        calledWith = o;
        return stubResponse;
    }

    public T wasCalledWith()
    {
       return calledWith;
    }
}
