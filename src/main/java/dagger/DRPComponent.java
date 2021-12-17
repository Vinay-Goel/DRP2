package dagger;

import javax.inject.Singleton;

import executor.DRPExecutor;

@Singleton
@Component(modules = {DRPModule.class})
public interface DRPComponent {
    void inject(DRPExecutor drpExecutor);
}
