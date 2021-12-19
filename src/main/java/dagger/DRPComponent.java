package dagger;

import javax.inject.Singleton;

import executor.DRPExecutor;
import executor.DRPGeneratedInputExecutor;

@Singleton
@Component(modules = {DRPModule.class})
public interface DRPComponent {
    void inject(DRPExecutor drpExecutor);
    void inject(DRPGeneratedInputExecutor drpGeneratedInputExecutor);
}
