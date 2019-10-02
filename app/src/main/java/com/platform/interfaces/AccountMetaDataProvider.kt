/**
 * BreadWallet
 *
 * Created by Ahsan Butt <ahsan.butt@breadwallet.com> on 9/17/19.
 * Copyright (c) 2019 breadwallet LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.platform.interfaces

import com.breadwallet.protocols.messageexchange.entities.PairingMetaData
import kotlinx.coroutines.flow.Flow
import com.platform.entities.TxMetaData
import com.platform.entities.WalletInfoData
import com.breadwallet.crypto.Account
import java.util.Date

@Suppress("TooManyFunctions")
/** Manages access to metadata for an [Account]. */
interface AccountMetaDataProvider {
    // TODO: Refine/refactor this API. For now it includes all leftover KVStoreManager functions

    /** Initializes metadata for a newly created [Account], optionally enabling
    [defaultEnabledWallets], a list of currency ids (uid). */
    fun create(accountCreationDate: Date, defaultEnabledWallets: List<String>?)

    /** Recovers metadata for a recovered [Account]. */
    // fun recover()

    /** Clean up. */
    //fun close()

    fun walletInfo(): Flow<WalletInfoData>

    fun getWalletInfoUnsafe(): WalletInfoData?

    fun syncWalletInfo()

    /** Enables the wallet for this Account. */
    fun enableWallet(currencyId: String): Flow<Unit>

    /** Disables the wallet for this Account. */
    fun disableWallet(currencyId: String): Flow<Unit>

    fun syncAssetIndex()

    /** Returns [TxMetaData] for given transaction hash. */
    fun getTxMetaData(txHash: ByteArray): TxMetaData?

    /** Persist given [TxMetaData] for transaction hash, but ONLY if the comment or exchange rate has changed. */
    fun putTxMetaData(newTxMetaData: TxMetaData, txHash: ByteArray)

    fun getPairingMetadata(pubKey: ByteArray): PairingMetaData?

    fun putPairingMetadata(pairingData: PairingMetaData): Boolean

    /** Returns the last cursor (used in retrieving inbox messages). */
    fun getLastCursor(): String?

    /** Persists the last cursor (used in retrieving inbox messages). */
    fun putLastCursor(lastCursor: String): Boolean
}