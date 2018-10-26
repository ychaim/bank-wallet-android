package bitcoin.wallet.modules.settings.main

import bitcoin.wallet.core.*
import io.reactivex.disposables.CompositeDisposable

class MainSettingsInteractor(
        private val localStorage: ILocalStorage,
        private val wordsManager: IWordsManager,
        languageManager: ILanguageManager,
        systemInfoManager: ISystemInfoManager,
        private val currencyManager: ICurrencyManager): MainSettingsModule.IMainSettingsInteractor {

    private var disposables: CompositeDisposable = CompositeDisposable()

    var delegate: MainSettingsModule.IMainSettingsInteractorDelegate? = null

    init {
        disposables.add(wordsManager.backedUpSubject.subscribe {
            onUpdateBackedUp(it)
        })

        disposables.add(currencyManager.getBaseCurrencyFlowable().subscribe {
            delegate?.didUpdateBaseCurrency(it.code)
        })
    }

    private fun onUpdateBackedUp(backedUp: Boolean) {
        if (backedUp) {
            delegate?.didBackup()
        }
    }


    override var isBackedUp: Boolean = wordsManager.isBackedUp

    override var currentLanguage: String = languageManager.currentLanguage.displayLanguage

    override var baseCurrency: String = localStorage.baseCurrency.code

    override var appVersion: String = systemInfoManager.appVersion

    override fun getLightMode(): Boolean {
        return localStorage.isLightModeOn
    }

    override fun setLightMode(lightMode: Boolean) {
        localStorage.isLightModeOn = lightMode
        delegate?.didUpdateLightMode()
    }

}