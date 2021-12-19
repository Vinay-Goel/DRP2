package dagger;

import javax.inject.Singleton;

import executor.DRPCollectInputExecutor;
import executor.DRPGeneratedInputExecutor;

@Singleton
@Component(modules = {DRPModule.class})
public interface DRPComponent {
    void inject(DRPCollectInputExecutor drpCollectInputExecutor);
    void inject(DRPGeneratedInputExecutor drpGeneratedInputExecutor);
}
