/*
 * Copyright (c) 2020 Nike, inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import React from 'react'
import { Component } from 'react'
import PropTypes from 'prop-types'
import * as cmsUtils from '../../utils/cmsUtils'
import './ApiError.scss'

/**
 * A component to use to make API messages sent to the messenger look pretty and be html and stylable
 *
 * @prop message The Message for this app to provide context to the user for what action failed
 * @prop response The Axios response
 */
export default class ApiError extends Component {
    static propTypes = {
        message: PropTypes.string.isRequired,
        response: PropTypes.object.isRequired
    }

    render() {
        const {message, response} = this.props
        return (
            <div className="api-error-wrapper">
                <div className="api-error-header">An API error has occurred</div>
                <div className="api-error-message">{message}</div>
                <div className="api-error-server-provided-details">
                    <div className="status-wrapper">
                        <div className="api-error-server-status-label">Server Status:</div>
                        <div className="api-error-server-status">{response.status}, {response.statusText}</div>
                    </div>
                    <div className="server-message-wrapper">
                        <div className="api-error-server-message-label">Server Message:</div>
                        <div className="api-error-server-message">{cmsUtils.parseCMSError(response)}</div>
                    </div>
                </div>
            </div>
        )
    }
}