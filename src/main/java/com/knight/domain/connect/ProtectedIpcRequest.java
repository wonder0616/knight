package com.knight.domain.connect;

import com.knight.db.dto.ProtectedIpc;
import lombok.Data;

@Data
public class ProtectedIpcRequest extends ProtectedIpc {

    private long limit;

    private long offset;
}
